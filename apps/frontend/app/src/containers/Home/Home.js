/**
 * Created by kwonohbin on 2016. 7. 26..
 */
import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Text, View, InteractionManager, TouchableOpacity, ActivityIndicator, Linking, StatusBar} from 'react-native';
import HTMLView from '../../components/Modules/react-native-htmlview';
import Icon from 'react-native-vector-icons/FontAwesome';
import Styles from './Styles/HomeStyle';
import {Colors} from '../../themes';
import {Actions} from 'react-native-router-flux';
import TabHomeGridView from '../../components/TabMenu/TabHome/TabHomeGridView';
import TabVideoView from '../../components/TabMenu/TabVideo/TabVideoView';
import TabPlayListView from '../../components/TabMenu/TabPlayList/TabPlayListView';
import TabSettingView from '../../components/TabMenu/TabSetting/TabSettingView';
import SwipeableViews from 'react-swipeable-views-native';
import {validationTitle} from '../../helpers/validation';
import {getBackgroundColor} from '../../helpers/getBackgroundColor';
import AppLink from 'react-native-app-link';

const YOUTUBE_KEY = "AIzaSyDnUdWg0rgGdnb5k5d4FGZr_yuCVriz2tk";

class Home extends Component {

	constructor(props) {
		super(props);

		this.state = {
			animating: true,
			title: '',
			tabBackgroundColor: undefined,
			index: 0,
			channelId: undefined,
			resultHTML: '',
			myBlogData: [{admsg: '', items: {}}],
			bjData: [{admsg: '', items: []}],
			videoData: [{nextPageToken: '', items: []}],
			playListData: [{nextPageToken: '', items: []}]
		};

		this._showActivityIndicator = this._showActivityIndicator.bind(this);
		this._gridViewRefresh = this._gridViewRefresh.bind(this);
		this._homeGridItemClick = this._homeGridItemClick.bind(this);
		this._getVideoDataMore = this._getVideoDataMore.bind(this);
		this._getPlayListDataMore = this._getPlayListDataMore.bind(this);
		this._searchHomeGridItemClick = this._searchHomeGridItemClick.bind(this);
	}

	componentDidMount() {
		InteractionManager.runAfterInteractions(() => {
			this.getBJList();
		});
	}

	getBJList = async () => {
		var request = new XMLHttpRequest();
		request.onreadystatechange = (e) => {
			if (request.readyState !== 4) {
				return;
			}

			if (request.status === 200) {
				// console.log('success', request.responseText);
				this.setState({resultHTML: request.responseText});

				this.renderNode(request.responseText);
			} else {
				console.warn('error');
			}
		};
		//220976719722  220908172224, 220981195570
		request.open('GET', 'http://blog.naver.com/PostView.nhn?blogId=logout0607&logNo=220981195570&redirect=Dlog&widgetTypeCall=true');
		await request.send();
	};

	getVideoData = async (rowData) => {
		try {
			let response = await fetch('https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=' + rowData.channelId + '&maxResults=25&order=date&type=video&key=' + YOUTUBE_KEY);
			let responseJson = await response.json();

			// this.setState({index: 1, channelId: rowData.channelId, videoData: [], title: rowData.title, tabBackgroundColor: getBackgroundColor()});
			this.setState({index: 1, channelId: rowData.channelId, videoData: {nextPageToken: responseJson.nextPageToken, items: responseJson.items}, title: rowData.title, tabBackgroundColor: getBackgroundColor()});
			// console.log(responseJson);
		} catch(error) {
			console.error(error);
		}
	};

	_getVideoDataMore = async (videoData) => {
		if(videoData.nextPageToken.length > 0) {
			const {channelId} = this.state;

			try {
				let response = await fetch('https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=' + channelId + '&maxResults=25&order=date&type=video&pageToken=' + videoData.nextPageToken + '&key=' + YOUTUBE_KEY);
				let responseJson = await response.json();

				this.setState({videoData: {nextPageToken: responseJson.nextPageToken, items: this.state.videoData.items.concat(responseJson.items)}});
			} catch(error) {
				console.error(error);
			}
		}
	};

	getPlayListData = async (channelId) => {
		try {
			let response = await fetch('https://www.googleapis.com/youtube/v3/playlists?part=snippet,contentDetails&maxResults=25&channelId=' + channelId + '&key=' + YOUTUBE_KEY);
			let responseJson = await response.json();

			this.setState({index: 2, channelId: channelId, playListData: {nextPageToken: responseJson.nextPageToken, items: responseJson.items}});
		} catch(error) {
			console.error(error);
		}
	};

	_getPlayListDataMore = async (rowData) => {
		if(rowData.nextPageToken.length > 0) {
			const {channelId} = this.state;

			try {
				let response = await fetch('https://www.googleapis.com/youtube/v3/playlists?part=snippet,contentDetails&maxResults=25&channelId=' + channelId + '&pageToken=' + rowData.nextPageToken + '&key=' + YOUTUBE_KEY);
				let responseJson = await response.json();

				this.setState({playListData: {nextPageToken: responseJson.nextPageToken, items: this.state.playListData.items.concat(responseJson.items)}});
			} catch(error) {
				console.error(error);
			}
		}
	};

	renderNode = (node, index, list) => {
		if(!node) return null;

		if( node.type == 'text' && node.data.indexOf("Json") != -1) {
			const responseData = node.data.split("Json:");

			// console.log(responseData);

			if(responseData.length == 2) {
				const item = JSON.parse(responseData[1]).items.sort(function(a, b) {return parseInt(a.statistics.subscriberCount) > parseInt(b.statistics.subscriberCount) ? -1 : parseInt(a.statistics.subscriberCount) < parseInt(b.statistics.subscriberCount) ? 1 : 0;});

				this.setState({
					title: item[0].title,
					channelId: item[0].channelId,
					myBlogData: {items: item},
					tabBackgroundColor: getBackgroundColor()
				});
			}
		}
	};

	handleChangeTabs = (index) => {
		this.setState({index: index});

		console.log("===== handleChangeTabs =====>> " + index);

		const {myBlogData, videoData, channelId} = this.state;

		if(index == 0) {
			// this.setState({index: index});
		} else if(index == 1) { //동영상 탭
			if(videoData.items == undefined) {
				this._homeGridItemClick(myBlogData.items[0]);
			} else {
				// this.setState({index: index});
			}
		} else if(index == 2) { //재생목록 탭
			if(channelId == undefined) {
				this.getPlayListData(myBlogData.items[0].channelId);
			} else {
				this.getPlayListData(channelId);
			}
		} else if(index == 3) {
			// this.setState({index: index});
		}
	};

	_showActivityIndicator(animating) {
		console.log("===== _showActivityIndicator =====");
		this.setState({animating: animating});
	}

	_gridViewRefresh = () => {
		console.log("===== _gridViewRefresh =====");
	};

	_homeGridItemClick = (rowData) => {
		const {channelId, videoData} = this.state;

		if(videoData.items == undefined || channelId != rowData.channelId) {
			this.setState({index: 1, channelId: rowData.channelId, videoData: [], title: rowData.title, tabBackgroundColor: getBackgroundColor()});
			this.getVideoData(rowData);
		} else {
			this.setState({index: 1, tabBackgroundColor: getBackgroundColor()});
		}
	};

	_searchHomeGridItemClick = (rowData) => {
		this.setState({channelId: rowData.channelId, videoData: [], title: rowData.title, tabBackgroundColor: getBackgroundColor()});
		this.getVideoData(rowData);
	};

	goSearch() {
		const {myBlogData, tabBackgroundColor} = this.state;

		(myBlogData.items && myBlogData.items.length > 0) && Actions.search({itemList: myBlogData, searchHomeGridItemClick: this._searchHomeGridItemClick, tabBackgroundColor: tabBackgroundColor});
	}

	render() {
		const {resultHTML, index, myBlogData, title, videoData, playListData, tabBackgroundColor} = this.state;
		const tabs = [{value: '크리에이터'}, {value: '동영상'}, {value: '재생목록'}, {value: '설정'}];

		// console.log("index: " + index);

		return (
			<View style={Styles.container}>
				<StatusBar backgroundColor={tabBackgroundColor} barStyle={'light-content'}/>
				<HTMLView
					value={resultHTML}
					renderNode={this.renderNode}/>

				<View style={[Styles.tabView, {backgroundColor: (tabBackgroundColor == null || tabBackgroundColor == undefined) ? Colors.red900 : tabBackgroundColor}]}>
					<View style={[Styles.tabTitleMenuView]}>
						<Text style={Styles.tabTitle}>{validationTitle(title)}</Text>
						<TouchableOpacity onPress={() => this.goSearch()} style={[Styles.tabSearch]}>
							<Icon name="search" size={20} color={Colors.white} />
						</TouchableOpacity>
					</View>
					<View style={{flexDirection: 'row', paddingLeft: 10}}>
						{
							tabs.map((tab, i) =>
								<Text key={i} onPress={() => {this.handleChangeTabs(i)}} style={[Styles.tabTitleMenuDefault, index == i && Styles.tabTitleMenuEnabled]}>
									{tab.value}
								</Text>)
						}
					</View>
				</View>

				<SwipeableViews index={index} onChangeIndex={(e) => this.handleChangeTabs(e)}>
					<TabHomeGridView
						itemList={myBlogData}
						showActivityIndicator={this._showActivityIndicator}
						gridViewRefresh={this._gridViewRefresh}
						homeGridItemClick={this._homeGridItemClick}/>
					<TabVideoView
						itemList={videoData}
						gridViewRefresh={this._gridViewRefresh}
						getVideoDataMore={this._getVideoDataMore}/>
					<TabPlayListView
						itemList={playListData}
						gridViewRefresh={this._gridViewRefresh}
						getPlayListDataMore={this._getPlayListDataMore}
						tabBackgroundColor={tabBackgroundColor}/>
					<TabSettingView/>
				</SwipeableViews>
			</View>
		)
	}
}

export default connect(
	state => ({
	}),
	{}
)(Home);
/**
 * Created by moojae on 2017. 4. 10..
 */
import React, {Component} from 'react';
import {InteractionManager, View, Text, ListView, Image, TouchableOpacity, ActivityIndicator, RefreshControl} from 'react-native';
import {Actions} from 'react-native-router-flux';
import {Colors} from '../../../themes';
import {validationTitle} from '../../../helpers/validation';
import Styles from './Styles/TabPlayListViewDetailStyle';
import Icon from 'react-native-vector-icons/FontAwesome';
import PlayDetailRow from './PlayDetailRow';

export default class TabPlayListViewDetail extends Component {

	constructor(props) {
		super(props);

		const {rowData} = this.props;

		this.state = {
			animating: true,
			title: rowData.snippet.title,
			videoData: [{nextPageToken: '', items: []}],
		};
	}

	componentDidMount() {
		InteractionManager.runAfterInteractions(() => {
			this.getPlayListDetail();
		});
	}

	getPlayListDetail = async () => {
		const {rowData} = this.props;

		try {
			let response = await fetch('https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=' + rowData.id + '&key=AIzaSyDnUdWg0rgGdnb5k5d4FGZr_yuCVriz2tk');
			let responseJson = await response.json();

			this.setState({videoData: {nextPageToken: responseJson.nextPageToken, items: responseJson.items}});
		} catch(error) {
			console.error(error);
		}
	};

	getPlayListDetailMore = async (videoData) => {
		const {rowData} = this.props;

		try {
			let response = await fetch('https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=' + rowData.id + '&pageToken=' + videoData.nextPageToken + '&key=AIzaSyDnUdWg0rgGdnb5k5d4FGZr_yuCVriz2tk');
			let responseJson = await response.json();

			this.setState({videoData: {nextPageToken: responseJson.nextPageToken, items: this.state.videoData.items.concat(responseJson.items)}});
		} catch(error) {
			console.error(error);
		}
	};

	loadMoreContentAsync = () => {
		const {videoData} = this.state;

		videoData.nextPageToken && videoData.nextPageToken.length > 0 && this.getPlayListDetailMore(videoData);
	};

	render() {
		const {videoData, title} = this.state;
		const {tabBackgroundColor} = this.props;

		let dataSource = undefined;
		if(videoData.items) {
			const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
			dataSource = ds.cloneWithRows(videoData.items);
		}

		return (
			<View style={Styles.container}>
				<View style={[Styles.tabView, {backgroundColor: tabBackgroundColor}]}>
					<TouchableOpacity onPress={() => Actions.pop()} style={[Styles.tabBack]}>
						<Icon name="chevron-left" size={20} color={Colors.white} />
					</TouchableOpacity>

					<Text numberOfLines={1} style={[Styles.tabTitle, ]}>{validationTitle(title)}</Text>
				</View>
				{
					dataSource ?
						<ListView
							dataSource={dataSource}
							renderRow={(rowData) => <PlayDetailRow rowData={rowData}/>}
							canLoadMore={true}
							onEndReached={this.loadMoreContentAsync}
							onEndReachedThreshold={10}
							enableEmptySections={true}
							removeClippedSubviews={true}
							initialListSize={15}
							pageSize={10}
						/>
						:
						<ActivityIndicator animating={true} style={Styles.centering} color={Colors.grey500} size="large"/>
				}
			</View>
		);
	}
}
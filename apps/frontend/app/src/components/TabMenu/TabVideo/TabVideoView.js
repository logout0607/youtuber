/**
 * Created by moojae on 2017. 4. 8..
 */
import React, {Component} from 'react';
import {InteractionManager, View, Text, ListView, Image, TouchableOpacity, ActivityIndicator, RefreshControl} from 'react-native';
import {Actions} from 'react-native-router-flux';
import {Colors} from '../../../themes';
import VideoRow from './VideoRow';
import Styles from './Styles/TabVideoViewStyle';

export default class TabVideoView extends Component {

	loadMoreContentAsync = async () => {
		const {itemList, getVideoDataMore} = this.props;

		itemList.nextPageToken && itemList.nextPageToken.length > 0 && await getVideoDataMore(itemList);
	};

	render() {
		const {itemList, gridViewRefresh} = this.props;

		let dataSource = undefined;
		if(itemList.items) {
			const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
			dataSource = ds.cloneWithRows(itemList.items);
		}

		return (
			<View style={Styles.container}>
				{
					dataSource ?
						<ListView
							dataSource={dataSource}
							renderRow={(rowData) => <VideoRow rowData={rowData}/>}
							canLoadMore={true}
							onEndReached={this.loadMoreContentAsync}
							onEndReachedThreshold={10}
							enableEmptySections={true}
							removeClippedSubviews={true}
							initialListSize={5}
							pageSize={1}
							refreshControl={
								<RefreshControl
									refreshing={false}
									onRefresh={gridViewRefresh}
								/>
							}
							/>
						:
						<ActivityIndicator animating={true} style={Styles.centering} color={Colors.grey500} size="large"/>
				}
			</View>
		);
	}
}
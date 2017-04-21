/**
 * Created by moojae on 2017. 4. 10..
 */
import React, {Component} from 'react';
import {View, ListView, Image, ActivityIndicator, RefreshControl} from 'react-native';
import {Colors} from '../../../themes';
import Styles from './Styles/TabPlayListViewStyle';
import PlayRow from './PlayRow';

export default class TabPlayListView extends Component {

	loadMoreContentAsync = () => {
		const {itemList, getPlayListDataMore} = this.props;

		itemList.nextPageToken && itemList.nextPageToken.length > 0 && getPlayListDataMore(itemList);
	};

	render() {
		const {itemList, gridViewRefresh, tabBackgroundColor} = this.props;

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
							renderRow={(rowData) => <PlayRow rowData={rowData} tabBackgroundColor={tabBackgroundColor}/>}
							canLoadMore={true}
							onEndReached={this.loadMoreContentAsync}
							onEndReachedThreshold={10}
							enableEmptySections={true}
							removeClippedSubviews={true}
							initialListSize={15}
							pageSize={10}
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
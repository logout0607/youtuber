/**
 * Created by moojae on 2017. 4. 7..
 */
import React, {Component} from 'react';
import {Platform, View, Text, ListView, Image, TouchableOpacity, TouchableHighlight, RefreshControl, ActivityIndicator, Linking, InteractionManager} from 'react-native';
import {Colors}from '../../../themes';
import Styles from './Styles/TabHomeGridViewStyle';
import HomeRow from './HomeRow';

export default class TabHomeGridView extends Component {
	loadMoreContentAsync = async () => {
		// const {itemList, getVideoDataMore} = this.props;
		//
		// itemList.nextPageToken.length > 0 && getVideoDataMore(itemList.nextPageToken);
	};

	render() {
		const {itemList, gridViewRefresh, homeGridItemClick} = this.props;

		let dataSource = undefined;
		if(itemList.items) {
			const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
			dataSource = ds.cloneWithRows(itemList.items);

			// showActivityIndicator(false);
		}

		return (
			<View style={Styles.container}>
				{
					dataSource ?
						<ListView
							dataSource={dataSource}
							renderSeparator={(sectionId, rowId) => <View key={rowId} style={Styles.renderSeparator} />}
							renderRow={(rowData) => <HomeRow rowData={rowData} homeGridItemClick={homeGridItemClick}/>}
							canLoadMore={true}
							onLoadMoreAsync={this.loadMoreContentAsync}
							enableEmptySections={true}
							removeClippedSubviews={true}
							initialListSize={5}
							pageSize={1}
							refreshControl={
								<RefreshControl
									refreshing={false}
									onRefresh={gridViewRefresh}
								/>
							}/>
						:
						<ActivityIndicator animating={true} style={Styles.centering} color={Colors.grey500} size="large"/>
				}
			</View>
		);
	}
}
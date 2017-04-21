/**
 * Created by moojae on 2017. 4. 18..
 */
import React, {Component} from 'react';
import {View, Text, Image, TouchableOpacity} from 'react-native';
import {Actions} from 'react-native-router-flux';
import Styles from './Styles/PlayRowStyle';

export default class PlayRow extends Component {

	render() {
		const {rowData, tabBackgroundColor} = this.props;

		return (
			<TouchableOpacity onPress={() => {Actions.playListDetail({rowData: rowData, tabBackgroundColor: tabBackgroundColor})}} style={Styles.renderRowContainer}>
				<Image source={{uri: rowData.snippet.thumbnails.high.url}} style={Styles.renderRowImage}/>
				<View style={Styles.renderRowTitleView}>
					<Text numberOfLines={2} style={Styles.renderRowTitle}>{rowData.snippet.title}</Text>

					<View style={{flexDirection: 'row'}}>
						<Text style={{fontSize: 11}}>동영상 {rowData.contentDetails.itemCount}</Text>
					</View>
				</View>
			</TouchableOpacity>
		);
	}
}
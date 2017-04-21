/**
 * Created by moojae on 2017. 4. 18..
 */
import React, {Component, PropTypes} from 'react';
import {ScrollView, Text, View, TouchableOpacity, Image, Platform, Linking} from 'react-native';
import {Images as ThemeImages}from '../../../themes';
import {validationTitle} from '../../../helpers/validation';
import AutoHeightImage from '../../Image/AutoHeightImage';
import Styles from './Styles/HomeRowStyle';

export default class HomeRow extends Component {
	constructor(props) {
		super(props);

		this.state = {
			backgroundImageHeight: 0
		};

		this._setHeight = this._setHeight.bind(this);
	}

	_setHeight = (height) => {
		const {backgroundImageHeight} = this.state;

		backgroundImageHeight == 0 && this.setState({backgroundImageHeight: height});
	};

	_numberWithCommas = (number) => {
		return number.length > 0 ? number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",") : " - ";
	};

	render() {
		const {backgroundImageHeight} = this.state;
		const {rowData, homeGridItemClick} = this.props;

		return (
			<View style={{flex: 1}}>
				<TouchableOpacity onPress={() => homeGridItemClick(rowData)} activeOpacity={0.5} style={[Styles.renderRowContainer]}>
					<AutoHeightImage url={rowData.images.bannerMobileExtraHdImageUrl} resizeMode={'contain'} backgroundImageHeight={backgroundImageHeight} setHeight={this._setHeight}/>
				</TouchableOpacity>
				<View style={{paddingLeft: Math.ceil(backgroundImageHeight/1.5)+30, paddingBottom: 5, justifyContent: 'center'}}>
					<Text style={[Styles.renderRowTitle]}>{validationTitle(rowData.title)}</Text>
				</View>
				<View style={{flex: 1, flexDirection: 'row'}}>
					<View style={{paddingLeft: Math.ceil(backgroundImageHeight/1.5)+30, paddingBottom: 5, justifyContent: 'center'}}>
						<Text style={[Styles.renderRowInfo]}>구독자 {this._numberWithCommas(rowData.statistics.subscriberCount)}명</Text>
						<Text style={[Styles.renderRowInfo]}>조회수 {this._numberWithCommas(rowData.statistics.viewCount)}회</Text>
						<Text style={[Styles.renderRowInfo]}>동영상 {this._numberWithCommas(rowData.statistics.videoCount)}개</Text>
					</View>
					<View style={{flex: 1, paddingRight: 5, justifyContent: 'flex-end', flexDirection: 'row', alignItems: 'center'}}>
						{
							rowData.social.facebook.length > 0 &&
							<TouchableOpacity onPress={() => Linking.openURL("https://www.facebook.com/" + rowData.social.facebook)}
																activeOpacity={1}>
								<Image source={ThemeImages.iconFacebook}
											 style={{width: 30, height: 30, borderRadius: 15, marginRight: 10}}/>
							</TouchableOpacity>
						}
						{
							rowData.social.instagram.length > 0 &&
							<TouchableOpacity onPress={() => Linking.openURL("https://www.instagram.com/" + rowData.social.instagram)}
																activeOpacity={1}>
								<Image source={ThemeImages.iconInstagram}
											 style={{width: 30, height: 30, borderRadius: 15, marginRight: 10}}/>
							</TouchableOpacity>
						}
						{
							rowData.social.afreecaTV.length > 0 &&
							<TouchableOpacity onPress={() => Linking.openURL("http://play.afreecatv.com/" + rowData.social.afreecaTV)}
																activeOpacity={1}>
								<Image source={ThemeImages.iconAfreecaTV}
											 style={{width: 30, height: 30, borderRadius: 15, marginRight: 10}}/>
							</TouchableOpacity>
						}
						{
							rowData.social.twitch.length > 0 &&
							<TouchableOpacity onPress={() => Linking.openURL("https://m.twitch.tv/" + rowData.social.twitch)}
																activeOpacity={1}>
								<Image source={ThemeImages.iconTwitch}
											 style={{width: 30, height: 30, borderRadius: 15, marginRight: 10}}/>
							</TouchableOpacity>
						}
					</View>
				</View>

				<Image source={{uri: rowData.images.thumbnail}} style={[Styles.renderRowThumbnailImage,
					{ width: Math.ceil(backgroundImageHeight/1.5),
						height: Math.ceil(backgroundImageHeight/1.5),
						borderRadius: Platform.OS === 'android' ? backgroundImageHeight : Math.ceil(backgroundImageHeight/3),
						top: Math.ceil(backgroundImageHeight/1.5)
					}]}/>
			</View>
		);
	}
}
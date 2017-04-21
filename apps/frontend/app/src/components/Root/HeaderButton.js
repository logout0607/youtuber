/**
 * Created by kwonohbin on 2016. 7. 26..
 */
import React from 'react';
import {TouchableOpacity, Image, Alert} from 'react-native';

import Icon from 'react-native-vector-icons/FontAwesome';

import Styles, {Images} from './Styles/HeaderButtonStyle';
import {Actions as NavigationActions} from 'react-native-router-flux';

import {Colors, Metrics } from '../../themes';

const toggleDrawer = () => {
	NavigationActions.refresh({
		key: 'drawer',
		open: value => !value
	})
};

export default {
	backButton() {
		return (
			<TouchableOpacity >
				<Icon name="angle-left"
					  size={Metrics.icons.small}
					  color={Colors.mainColor}
					  style={Styles.navButtonLeft}
				/>
			</TouchableOpacity>
		)
	},

	hamburgerButton() {
		return (
			<TouchableOpacity onPress={toggleDrawer}>
				<FontAwesome name="navicon"
					size={Metrics.icons.small}
					color={Colors.mainColor}
					style={Styles.navButtonLeft}
				/>
			</TouchableOpacity>
		)
	},
	calendarButton() {
		return (
			<TouchableOpacity onPress={() => NavigationActions.schedule()}>
				<Image style={Styles.navButtonRight}  source={Images.calendar}/>
			</TouchableOpacity>
		)
	},
	searchButton() {
		return (
			<TouchableOpacity onPress={ () => NavigationActions.search({duration: 250}) } style={{justifyContent: 'center', alignItems: 'center'}}>
				<Icon name="search" size={30} color="#900" />
			</TouchableOpacity>
		)
	}
}
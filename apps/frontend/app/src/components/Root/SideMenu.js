/**
 * Created by moojae on 2016. 11. 14..
 */
import React, {Component} from 'react';
import {ScrollView, TouchableOpacity, Text} from 'react-native';
import {connect} from 'react-redux';
import Styles from './Styles/SideMenuStyle';

import {Actions} from "react-native-router-flux";

class SideMenu extends Component {

	static contextTypes = {
		drawer: React.PropTypes.object
	};

	toggleDrawer () {
		this.context.drawer.toggle()
	}

	handleHome = () => {
		this.toggleDrawer();
		Actions.home();
	};

	handleTest01 = () => {
		this.toggleDrawer();
		Actions.test01();
	}

	handleEmployment = () => {
		this.toggleDrawer();
		Actions.employment();
	}

	render() {

		return(
			<ScrollView style={Styles.container}>
				<TouchableOpacity style={Styles.sideMenuButton} onPress={this.handleHome}>
					<Text style={Styles.sideMenuText}>HOME</Text>
				</TouchableOpacity>
				<TouchableOpacity style={Styles.sideMenuButton} onPress={this.handleTest01}>
					<Text style={Styles.sideMenuText}>TEST01</Text>
				</TouchableOpacity>
				<TouchableOpacity style={Styles.sideMenuButton} onPress={this.handleEmployment}>
					<Text style={Styles.sideMenuText}>Employment</Text>
				</TouchableOpacity>
			</ScrollView>
		);
	}

}
export default connect(
	state => ({})
)(SideMenu);
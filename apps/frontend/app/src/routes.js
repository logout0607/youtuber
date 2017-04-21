/**
 * Created by kwonohbin on 2016. 7. 26..
 */

import React, {Component} from 'react';
import {Platform, Alert, BackAndroid, ToastAndroid} from 'react-native';
import {Scene, Router, Modal} from 'react-native-router-flux';
import {Colors, Metrics} from './themes';
import HeaderButton from './components/Root/HeaderButton';
import Home from './containers/Home/Home';
import Video from './containers/Video/Video';
import Search from './containers/Search/Search';
import TabPlayListViewDetail from './components/TabMenu/TabPlayList/TabPlayListViewDetail';

const RoutersStyle = {
	container: {
		flex: 1
	},
	navBar: {
		// flex: 1,
		// height: Metrics.navBarHeight,
		backgroundColor: Colors.white,
		borderBottomColor: 'red'
		// alignItems: "center",
		// justifyContent: 'center',
		// flexDirection: 'row'
	},
	title: {
		// marginTop:Metrics.topBarHeight/2,
		// top: -Metrics.navBarHeight/2-12,
		alignItems: "flex-start",
		justifyContent: 'flex-start',
		color: Colors.mainColor,
		backgroundColor: 'red'
	},
	leftButton: {
		tintColor: Colors.mainColor
	},
	rightButton: {
		color: Colors.mainColor
	},
	transparentNavBar: {
		backgroundColor: Colors.transparent
	}
};

let toastShow = false;
BackAndroid.addEventListener('hardwareBackPress', function() {
	// this.onMainScreen and this.goBack are just examples, you need to use your own implementation here
	// Typically you would use the navigator here to go to the last state.
	if(toastShow) {
		return false;
	} else {
		let sec = 2000;
		let confirmMSG = "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.";
		ToastAndroid.show(confirmMSG, sec);
		toastShow = true;
		setTimeout(() => {toastShow = false}, sec);

		return true;
	}
});

export default class Routers extends Component {

	render() {

		return (
			<Router>
				{/*<Scene key="drawer" component={CommonDrawer}>*/}
					{/*<Scene key="drawerChildrenWrapper" navigationBarStyle={RoutersStyle.navBar} titleStyle={RoutersStyle.title} rightButtonTextStyle={RoutersStyle.rightButton}>*/}
						{/*<Scene initial key='root' navigationBarStyle={RoutersStyle.navBar} titleStyle={RoutersStyle.title}>*/}
							{/*<Scene key='home' component={Home} title='오늘의 일' renderLeftButton={HeaderButton.hamburgerButton}   renderBackButton={HeaderButton.hamburgerButton}/>*/}
							{/*<Scene key='test01' component={Test01} title='Test01' renderLeftButton={HeaderButton.hamburgerButton}  renderBackButton={HeaderButton.hamburgerButton}/>*/}
							{/*<Scene key='employment' component={Employment} title='Employment' renderLeftButton={HeaderButton.hamburgerButton}  renderBackButton={HeaderButton.hamburgerButton}/>*/}
						{/*</Scene>*/}
					{/*</Scene>*/}

				{/*</Scene>*/}
				<Scene key='modal' component={Modal}>
					<Scene key='root'>
						<Scene initial key='home' component={Home} hideNavBar={true}/>
						<Scene key='video' component={Video} hideNavBar={true} direction="vertical"/>
						<Scene key='playListDetail' component={TabPlayListViewDetail} hideNavBar={true}/>
						<Scene key='search' component={Search} hideNavBar={true}/>
					</Scene>
				</Scene>

			</Router>
		)
	}
}
// panHandlers={null}

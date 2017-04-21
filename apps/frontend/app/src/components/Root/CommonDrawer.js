/**
 * Created by moojae on 2016. 11. 14..
 */
import React, {Component} from 'react';
import Drawer from 'react-native-drawer';
import {DefaultRenderer, Actions} from 'react-native-router-flux';
import {connect} from 'react-redux';

import SideMenu from './SideMenu';

class CommonDrawer extends Component {

	render() {

		const {navigationState, onNavigate, user} = this.props;
		const children = navigationState.children;
		const drawerStyles = {
			drawer: { shadowColor: '#000000', shadowOpacity: 0.8, shadowRadius: 3},
			main: {paddingLeft: 3}
		}

		return(
			<Drawer
				ref="navigation"
				type="overlay"
				acceptPan={false}
				open={navigationState.open}
				onOpen={() => Actions.refresh({key: navigationState.key, open: true})}
				onClose={() => Actions.refresh({key: navigationState.key, open: false})}
				tapToClose={true}
				negotiatePan={true}
				openDrawerOffset={0.5}
				panCloseMask={0.5}
				panOpenMask={0.5}
				acceptDoubleTap={true}
				styles={drawerStyles}
				tweenHandler={(ratio) => ({
					main: { opacity: Math.max(0.54, 1 - ratio) }
				})}
				content={<SideMenu drawerKey={navigationState.key}/>}
				>

				<DefaultRenderer navigationState={children[0]} onNavigate={onNavigate}/>

			</Drawer>
		);
	}
}
export default connect(
	state => ({}), {}
)(CommonDrawer)
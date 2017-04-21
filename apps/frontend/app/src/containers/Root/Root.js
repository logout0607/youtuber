/**
 * Created by kwonohbin on 2016. 7. 26..
 */
import React, {Component, PropTypes} from 'react';
import { View, StatusBar, AsyncStorage } from 'react-native';

import { Provider, connect } from 'react-redux';
import Routers from '../../routes';


export default class Root extends Component {
	render() {
		const { store } = this.props;
		return (
			<Provider store={store}>
				<View style={{flex:1}}>
					<StatusBar barStyle='default' />
					<Routers/>
				</View>
			</Provider>
		)
	}
}
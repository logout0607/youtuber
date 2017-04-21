/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
import { AppRegistry, Platform } from 'react-native';
import React, { Component } from 'react';

import createStore from './src/redux/createStore';
import Root from './src/containers/Root/Root';

const store = createStore();

class App extends Component {
  render() {
    return <Root {...this.props} store={store} />
  }
}

AppRegistry.registerComponent('app', () => App);

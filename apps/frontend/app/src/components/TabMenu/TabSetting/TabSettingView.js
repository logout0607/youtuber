/**
 * Created by parkjemin on 2017. 4. 15..
 */
import React, {Component} from 'react';
import {View, Text, ScrollView, TouchableOpacity} from 'react-native';
import {Colors}from '../../../themes';
import Icon from 'react-native-vector-icons/FontAwesome';
import Styles from './Styles/TabSettingViewStyle';

export default class TabSettingView extends Component {

    render() {

        return (
            <ScrollView style={Styles.container}>
                <View style={Styles.view}>
                    <Text>버전</Text>
                    <Text>1.0.7</Text>
                </View>

                <View style={Styles.separator}/>

                <TouchableOpacity style={Styles.view}>
                    <Text>개발자에게 메일보내기</Text>
                    <Icon name="chevron-right" size={20} color={Colors.black} />
                </TouchableOpacity>

                <View style={Styles.separator}/>
            </ScrollView>
        );
    }
}
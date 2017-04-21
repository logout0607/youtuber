/**
 * Created by parkjemin on 2017. 4. 15..
 */
import React, {Component} from 'react';
import {View, Text, TextInput, TouchableOpacity, InteractionManager} from 'react-native';
import TabHomeGridView from '../../components/TabMenu/TabHome/TabHomeGridView';
import Styles from './Styles/SearchStyle';
import Icon from 'react-native-vector-icons/Ionicons';
import {Actions} from 'react-native-router-flux';
import {Colors} from '../../themes';

export default class Search extends Component {

    constructor(props) {
        super(props);

        this.state = {
            text: '',
            itemList: []
        };
    }

    componentDidMount() {
        const {itemList} = this.props;

        InteractionManager.runAfterInteractions(() => {
            this.setState({itemList: itemList});
        });
    }

    _showActivityIndicator(animating) {
        // console.log("===== _showActivityIndicator =====");
        // this.setState({animating: animating});
    }

    _gridViewRefresh = () => {
        // console.log("===== _gridViewRefresh =====");
    };

    _homeGridItemClick = (rowData) => {
        console.log("===== Search _homeGridItemClick =====");
        console.log(rowData);
        const {searchHomeGridItemClick} = this.props;

        searchHomeGridItemClick(rowData);
        Actions.pop();
    };

    render () {
        const {itemList, text} = this.state;
        const {tabBackgroundColor} = this.props;

        return (
            <View style={Styles.container}>
                <View style={[Styles.tabView, {backgroundColor: tabBackgroundColor}]}>
                    <TouchableOpacity onPress={() => Actions.pop()} style={[Styles.tabBack]}>
                        {/*<Icon name="chevron-left" size={20} color={Colors.white} />*/}
                        <Icon name="md-arrow-back" size={20} color={Colors.white} />
                    </TouchableOpacity>

                    {/*<Text numberOfLines={1} style={[Styles.tabTitle]}>{validationTitle(title)}</Text>*/}
                    {/*<Text numberOfLines={1} style={[Styles.tabTitle]}>검색어</Text>*/}


                    <TextInput
                      onChangeText={(t) => this.setState({text: t})}
                      value={text}
                      autoCorrect={false}
                      autoFocus={true}
                      underlineColorAndroid="transparent"
                      returnKeyType={"search"}
                      placeholder={"크리에이터 검색"}
                      placeholderTextColor={Colors.white}
                      style={{flex: 1, backgroundColor: Colors.transparent, paddingTop: 20, color: Colors.white}}/>

                    <TouchableOpacity onPress={() => {text.length > 0 && this.setState({text: ''})}} style={[Styles.tabBack]}>
                        {/*<Icon name="chevron-left" size={20} color={Colors.white} />*/}
                        <Icon name="md-close" size={20} color={Colors.white} />
                    </TouchableOpacity>
                </View>

                <TabHomeGridView
                    itemList={itemList}
                    showActivityIndicator={this._showActivityIndicator}
                    gridViewRefresh={this._gridViewRefresh}
                    homeGridItemClick={this._homeGridItemClick}/>
            </View>
        );
    }
}
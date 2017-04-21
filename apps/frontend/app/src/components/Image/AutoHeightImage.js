/**
 * Created by moojae on 2017. 4. 7..
 */
import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {View, Image} from 'react-native';
import {Metrics} from '../../themes';


class AutoHeightImage extends Component {
	constructor(props) {
		super(props);

		this.state = {
			height: 0,
			didMount: false
		}
	}

	componentWillMount() {
		const {url, backgroundImageHeight, setHeight} = this.props;
		// const {height} = this.state;

		if(backgroundImageHeight == 0) {
			Image.getSize(url, (imageWidth, imageHeight) => {
				this.setState({height: parseInt((imageHeight * Metrics.screenWidth) / imageWidth)});
				setHeight(parseInt((imageHeight * Metrics.screenWidth) / imageWidth));
			});
		}

		// this.setState({
		// 	didMount : true
		// })
	}

	render() {
		const {height} = this.state;
		const {url, resizeModeValue} = this.props;

		return (
			<View>
				{
					<Image style={{width: Metrics.screenWidth, height: height}} source={{uri: url}} resizeMode={resizeModeValue}/>
				}
			</View>
		)
	}
}

export default connect(
	state => ({
	}),
	{}
)(AutoHeightImage);

{/*this.state.didMount &&*/}
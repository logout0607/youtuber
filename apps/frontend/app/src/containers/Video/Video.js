/**
 * Created by moojae on 2017. 4. 9..
 */
import React, {Component} from 'react';
import {Text, View, StatusBar} from 'react-native';
import {Colors} from '../../themes';
import {connect} from "react-redux";
import YouTube from 'react-native-youtube';
import Styles from './Styles/VideoStyle';
import {getTracker} from "../../helpers/GoogleAnalytics";
import Orientation from'react-native-orientation';

class Video extends Component {

	constructor(props) {
		super(props);

		this.state = {
			nativeView : {width: 0 , height: 0},
		};
	}

	componentWillMount() {
		Orientation.lockToPortrait();
	}

	componentDidMount() {
		const {rowData} = this.props;

		let name = rowData.snippet.channelTitle + " : " + rowData.snippet.title;

		getTracker().trackScreenView(name);
	}

	componentWillUnmount(){
		Orientation.lockToPortrait();
	}

	orientationDidChange = (orientation) => {
		if (orientation == 'LANDSCAPE') {
			Orientation.lockToLandscape();
		} else {
			Orientation.lockToPortrait();
		}
	}

	render() {
		const {rowData} = this.props;
		const {nativeView} = this.state;
		// console.log(rowData.id.videoId);

		return (
			<View style={{flex: 1, backgroundColor: Colors.black, alignItems: 'center'}}>
				<StatusBar animated={true} hidden={true}/>

				<YouTube
					ref={(component) => { this._youTubePlayer = component }}
					videoId={rowData.id.videoId} // The YouTube video ID
					play={true}           // control playback of video with true/false
					hidden={false}        // control visiblity of the entire view
					playsInline={true}    // control whether the video should play inline
					loop={false}          // control whether the video should loop when ended
					showinfo={false}
					controls={1}
					modestbranding={true}
					onReady={(e) => {this.setState({isReady: true})}}
					onChangeState={(e) => {
						this.setState({status: e.state});
						if(e.state == 'videoStarted' || e.state == 'buffering') {
							this.setState({ nativeView: Styles.youtube})
						}
					}}
					onChangeQuality={(e) => {this.setState({quality: e.quality})}}
					onError={(e) => {this.setState({error: e.error})}}
					onProgress={(e) => {this.setState({currentTime: e.currentTime, duration: e.duration})}} //B8:29:72:83:34:0D:1E:C8:B3:18:C0:27:00:6D:ED:82:B0:79:D0:19
					apiKey={'14:B2:59:85:CF:3C:BA:52:01:CB:2F:D3:01:38:78:D3:53:9D:57:31'} //AIzaSyDnUdWg0rgGdnb5k5d4FGZr_yuCVriz2tk  14:B2:59:85:CF:3C:BA:52:01:CB:2F:D3:01:38:78:D3:53:9D:57:31
					style={nativeView}
				/>
			</View>
		);
	}
}

export default connect(
	state => ({
	}),
	{}
)(Video);
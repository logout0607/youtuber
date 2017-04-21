import {Dimensions, Platform} from 'react-native';

const { width, height } = Dimensions.get('window');

// Used via Metrics.baseMargin
export default Metrics = {
	marginHorizontal: 10,
	marginVertical: 10,
	section: 25,
	baseMargin: 10,
	doubleBaseMargin: 20,
	doubleSmallMargin: 10,
	smallMargin: 5,
	largeMargin: 14,
	horizontalLineHeight: 1,
	screenWidth: width < height ? width : height,
	screenHeight: width < height ? height : width,
	navBarHeight: Platform.OS === 'ios' ? 74 : 64, //Platform.OS === 'ios' || Platform.Version > 19 ? 64 : 44, // (Platform.OS === 'ios') ? 58 : 49,
	topBarHeight: (Platform.OS === 'ios') ? 18 : 0,
	buttonRadius: 4,
	smallFont:7,
	mediumFont:10,
	largeFont:12,
	icons: {
		tiny: 15,
		small: 20,
		medium: 30,
		large: 45,
		xl: 60
	},
	images: {
		small: 20,
		medium: 40,
		large: 60,
		logo: 300
	}
}

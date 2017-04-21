/**
 * Created by kwonohbin on 2016. 7. 26..
 */
import {StyleSheet, Platform} from 'react-native'
import {Metrics, Colors, ApplicationStyles, Images as ThemeImages} from '../../../themes';

export default StyleSheet.create({
	...ApplicationStyles.screen,
	container: {
		...ApplicationStyles.screen.container,
		backgroundColor: Colors.gray,
		paddingTop: (Platform.OS === 'ios') ? Metrics.baseMargin + 10 : Metrics.baseMargin
	},
	sideMenuButton: {
		backgroundColor: Colors.transparent,
		borderTopWidth: 1,
		borderTopColor: Colors.lightGray,
		borderColor: Colors.darkGray,
		flexDirection: 'row',
		alignItems: 'center',
		flex: 1
	},
	sideMenuText: {
		fontSize: Metrics.baseMargin,
		color: Colors.darkGray,
		marginLeft: Metrics.baseMargin,
		marginTop: Metrics.doubleBaseMargin,
		marginBottom: Metrics.doubleBaseMargin,
		flex: 1
	},
	iconAdd: {
		width: 20,
		height: 20,
		marginRight: Metrics.baseMargin,
		justifyContent: 'flex-end'
	},
	myCeleb: {
		width: Metrics.screenWidth * 0.5,
		height: Metrics.screenWidth * 0.8
	}
});

let Images = {
	add: ThemeImages.add,
	my_celeb: ThemeImages.my_celeb
};

export {Images};
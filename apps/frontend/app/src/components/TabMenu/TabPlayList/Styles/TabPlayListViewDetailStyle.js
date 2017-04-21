/**
 * Created by moojae on 2017. 4. 10..
 */
import { StyleSheet, Platform } from 'react-native'
import { Metrics, Colors } from '../../../../themes';

export default StyleSheet.create({
	container: {
		flex: 1
	},
	tabView: {
		flexDirection: 'row',
		height: Metrics.navBarHeight / 1.5,
		alignItems: 'center'
	},

	tabBack: {
		marginTop: Platform.OS === 'ios' ? 20 : 0,
		padding: 10,
		alignItems: 'center'
	},
	tabTitle: {
		marginTop: Platform.OS === 'ios' ? 20 : 0,
		color: Colors.white,
		fontWeight: 'bold',
		marginRight: 50
	},
	centering: {
		position: 'absolute',
		top: 0,
		left: 0,
		right: 0,
		bottom: 0,
		justifyContent: 'center',
		alignItems: 'center'
	},
});
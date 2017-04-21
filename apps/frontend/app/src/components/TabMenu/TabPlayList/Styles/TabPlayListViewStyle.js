/**
 * Created by moojae on 2017. 4. 8..
 */
import { StyleSheet, Platform } from 'react-native'
import { Metrics, Colors } from '../../../../themes';

export default StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: Colors.white
	},
	centering: {
		position: 'absolute',
		top: 0,
		left: 0,
		right: 0,
		bottom: 0,
		justifyContent: 'center',
		alignItems: 'center'
	}
});
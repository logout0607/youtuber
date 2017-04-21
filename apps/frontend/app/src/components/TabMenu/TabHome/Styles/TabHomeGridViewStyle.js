/**
 * Created by moojae on 2017. 4. 7..
 */
import {StyleSheet} from 'react-native'
import {Colors} from '../../../../themes';

export default StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: Colors.white
	},
	renderRowInfoFalse: {
		fontSize: 10,
		color: Colors.darkGray,
		height: 0
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
	renderSeparator: {
		flex: 1,
		height: 0.5,
		backgroundColor: '#8E8E8E'
	}
});
/**
 * Created by kwonohbin on 2016. 7. 26..
 */
import { StyleSheet, Platform } from 'react-native'
import { Metrics, Colors } from '../../../themes';
import {getBackgroundColor} from '../../../helpers/getBackgroundColor';

export default StyleSheet.create({
	container: {
		flex: 1,
		flexDirection: 'column',
		backgroundColor: Colors.white,
	},
	tabView: {
		height: Metrics.navBarHeight,
		backgroundColor: getBackgroundColor()
	},
	tabTitleMenuView: {
		flex: 1,
		flexDirection: 'row',
		marginLeft: 10,
		marginTop: Platform.OS === 'ios' ? 20 : 0,
		alignItems: 'center'
	},
	tabTitle: {
		flex: 1,
		marginRight: 20,
		color: Colors.white,
		fontWeight: 'bold',
	},
	tabTitleMenuDefault: {
		fontSize: 10,
		marginRight: 20,
		paddingVertical: 5,
		color: Colors.transparentHalf
	},
	tabTitleMenuEnabled: {
		fontSize: 10,
		fontWeight: 'bold',
		marginRight: 20,
		color: Colors.white,
	},
	tabSearch: {
		// marginTop: Platform.OS === 'ios' ? 20 : 0,
		padding: 10,
		alignItems: 'center',
		justifyContent: 'center'
	},
	centering: {
		position:'absolute',
		top: 0,
		left: 0,
		right: 0,
		bottom: 0,
		justifyContent: 'center',
		alignItems: 'center'
	}
});

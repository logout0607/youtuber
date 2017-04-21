/**
 * Created by parkjemin on 2017. 4. 15..
 */
import { StyleSheet, Platform } from 'react-native'
import { Metrics, Colors } from '../../../themes';

export default StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: Colors.white,
    },
    tabView: {
        flexDirection: 'row',
        height: Metrics.navBarHeight/1.5,
        alignItems: 'center',
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
});
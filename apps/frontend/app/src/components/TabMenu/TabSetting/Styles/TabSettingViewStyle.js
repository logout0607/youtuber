/**
 * Created by parkjemin on 2017. 4. 15..
 */
import { StyleSheet, Platform } from 'react-native'
import { Metrics, Colors } from '../../../../themes';

export default StyleSheet.create({
    container: {
        flex: 1
    },
    view: {
        flexDirection: 'row',
        height: Metrics.navBarHeight,
        alignItems: 'center',
        justifyContent: 'space-between',
        paddingHorizontal: 10,
    },
    separator: {
        flex: 1,
        height: 1,
        backgroundColor: '#8E8E8E',
    }
});
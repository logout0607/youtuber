import Metrics from './Metrics';
import Colors from './Colors';

export default {
  screen: {
    mainContainer: {
      flex: 1,
      marginTop: Metrics.navBarHeight-5,
      backgroundColor: Colors.transparent
    },
    backgroundImage: {
      position: 'absolute',
      top: 0,
      left: 0,
      bottom: 0,
      right: 0
    },
    container: {
      flex: 1,
      paddingTop: Metrics.baseMargin
    }
  }
}
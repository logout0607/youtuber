/**
 * Created by kwonohbin on 2016. 7. 26..
 */

export default settings = {
	reduxLogging: true,
	apiHost: __DEV__ ?'http://localhost:8080' : 'http://admin.dev-fsa.carlab.co.kr/api',
	secHost: __DEV__ ?'http://localhost:8082' : 'http://admin.dev-fsa.carlab.co.kr/auth',
};

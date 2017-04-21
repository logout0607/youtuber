import superagent from 'superagent/lib/client';
import config from '../config';

const methods = ['get', 'post', 'put', 'patch', 'del'];

function formatUrl(path) {
	const adjustedPath = path[0] !== '/' ? '/' + path : path;

	if(path.startsWith('/auth')){
		return config.secHost + adjustedPath.replace('/auth', '');
	}

	return config.apiHost + adjustedPath;
}

export default class ApiClient {
	constructor(req) {
		methods.forEach((method) =>
			this[method] = (path, {params, data, csrfToken, authorization} = {}) => new Promise((resolve, reject) => {
				const startedAt = new Date().getTime();
				const request = superagent[method](formatUrl(path));

				if (params) {
					request.query(params);
				}

				if (csrfToken) {
					request.set('X-XSRF-TOKEN', csrfToken);
				}

				if (authorization) {
					request.set('authorization', authorization);
				}

				if (data) {
					request.send(data);
				}

				request.end((err, {body} = {}) =>
					{
						if(__DEV__){
							const end = new Date().getTime();
							const duration = end - startedAt;
						}

						if(err){
							return reject(body || err);
						}
						return resolve(body);
					}
				);
			}));
	}

	empty() {
	}
}

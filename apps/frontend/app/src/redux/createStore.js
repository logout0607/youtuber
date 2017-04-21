/**
 * Created by kwonohbin on 2016. 7. 26..
 */
import { createStore as _createStore, applyMiddleware, compose } from 'redux';
import createLogger from 'redux-logger';
import multi from 'redux-multi';
import config from '../config';

import reducers from './reducers';

import ApiClient from '../helpers/ApiClient';
const client = new ApiClient();

const logger = createLogger({
	predicate: (getState, { type }) => config.reduxLogging
});

function clientMiddleware(client) {
	return ({dispatch, getState}) => {
		return next => action => {
			if (typeof action === 'function') {
				return action(dispatch, getState);
			}

			const {promise, types, ...rest} = action; // eslint-disable-line no-redeclare
			if (!promise) {
				return next(action);
			}

			const [REQUEST, SUCCESS, FAILURE] = types;
			next({...rest, type: REQUEST});

			const actionPromise = promise(client);
			actionPromise.then(
				(result) => next({...rest, result, type: SUCCESS}),
				(error) => next({...rest, error, type: FAILURE})
			).catch((error)=> {
				console.error('MIDDLEWARE ERROR:', error);
				next({...rest, error, type: FAILURE});
			});

			return actionPromise;
		};
	};
}


let middleware = [];
middleware.push(multi);
middleware.push(clientMiddleware(client));

if (__DEV__) {
	middleware.push(logger);
}

export default () => {
	let store = {};



	if(__DEV__){
		const enhancers = compose(
			applyMiddleware(...middleware)
		);

		store = _createStore(
			reducers,
			enhancers
		);

		return store;
	}

	store = _createStore(
		reducers,
		applyMiddleware(...middleware)
	);

	return store;
}
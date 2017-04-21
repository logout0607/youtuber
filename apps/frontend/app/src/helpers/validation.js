/**
 * Created by moojae on 2017. 4. 8..
 */

export function validationTitle(title) {
	return title.replace(/&nbsp;/gi, " ");
}
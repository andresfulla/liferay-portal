global.Liferay = {
	Language: {
		get: key => key
	},
	Util: {
		sub: function _subMock(string, data) {
			return string + data;
		}
	}
}
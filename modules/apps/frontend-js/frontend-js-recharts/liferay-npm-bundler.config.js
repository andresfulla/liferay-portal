module.exports = {
	"create-jar": false,
	"exports": {
		"recharts": "recharts",
		"recharts_lib_index": "recharts/lib/index.js"
	},
	"imports": {
		"frontend-js-node-shims": {
			"events": "^1.0.0"
		},
		"frontend-js-react-web": {
			"react": "^16.0.0",
			"react-dom": "^16.0.0"
		}
	},
	"output": "build/node/packageRunBuild/resources",
	"work-dir": "build/node/bundler"
}


import React, {Component} from 'react';
import PropTypes from 'prop-types';
import CriteriaSidebarCollapse from './CriteriaSidebarCollapse.es';
import CriteriaSidebarSearchBar from './CriteriaSidebarSearchBar.es';

class CriteriaSidebar extends Component {
	static propTypes = {
		propertyKey: PropTypes.string,
		propertyGroups: PropTypes.array,
		onTitleClicked: PropTypes.func,
		editing: PropTypes.bool
	};

	state = {
		searchValue: '',
	};

	_handleOnSearchChange = value => {
		this.setState({searchValue: value});
	}

	_handleOnClickCollapse = (key, editing) => {
		this.props.onTitleClicked(key, editing);
	}

	render() {
		const {propertyKey, propertyGroups} = this.props;

		const {searchValue} = this.state;
				
		return (
			<div className="criteria-sidebar-root">
				<div className="sidebar-search">
					<CriteriaSidebarSearchBar
						onChange={this._handleOnSearchChange}
						searchValue={searchValue}
					/>
				</div>

				<div className="sidebar-collapse">
					<CriteriaSidebarCollapse
						propertyKey={propertyKey}
						propertyGroups={propertyGroups}
						searchValue={searchValue}
						onCollapseClick={this._handleOnClickCollapse}
					/>
				</div>
			</div>
		);
	}
}

export default CriteriaSidebar;
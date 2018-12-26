import React from 'react';
import DropZone from './DropZone.es';
import ClayButton from '../shared/ClayButton.es';

/**
 *
 *
 * @export
 * @class Conjunction
 * @extends {React.Component}
 */
export default class Conjunction extends React.Component {
	/**
	 *
	 *
	 * @memberof Conjunction
	 * @return {Node}
	 */
	render() {
		const {
			conjunctionName,
			editing,
			groupId,
			onMove,
			supportedConjunctions,
			index,
		} = this.props;

		return (<React.Fragment>
			<DropZone
				dropIndex={index}
				groupId={groupId}
				onCriterionAdd={this.props._handleCriterionAdd}
				onMove={onMove}
			/>

			{editing ?
				<ClayButton
					className="btn-sm conjunction-button"
					label={this.props._getConjunctionLabel(
						conjunctionName,
						supportedConjunctions
					)}
					onClick={this.props._handleConjunctionClick}
				/> :
				<div className="conjunction-label">
					{this.props._getConjunctionLabel(
						conjunctionName,
						supportedConjunctions
					)}
				</div>
			}

			<DropZone
				before
				dropIndex={index}
				groupId={groupId}
				onCriterionAdd={this.props._handleCriterionAdd}
				onMove={onMove}
			/>
		</React.Fragment>);
	}
}

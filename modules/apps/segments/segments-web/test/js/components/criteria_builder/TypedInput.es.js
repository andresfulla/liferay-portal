import React from 'react';
import TypedInput from 'components/criteria_builder/TypedInput.es';
import {cleanup, render} from 'react-testing-library';

const options = [
	{
		label: 'Default Value',
		value: 'defaultValue'
	}, {
		label: 'Some Other Option',
		value: 'someOtherOption'
	}
];

describe(
	'CriteriaRow',
	() => {
		afterEach(cleanup);

		it(
			'should render with type string',
			() => {
				const {asFragment} = render(
					<TypedInput
						type="string"
					/>
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);
		it(
			'should render type string with value',
			() => {
				const {asFragment} = render(
					<TypedInput
						type="string"
						value="defautvalue"
					/>
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);
		it(
			'should render type string with pseudotype options',
			() => {
				const {asFragment} = render(
					<TypedInput
						type="string"
						value="defautvalue"
						options={options}
					/>
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);
	}
);
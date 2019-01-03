import React from 'react';
import ClayToggle from 'components/shared/ClayToggle.es';
import {cleanup, render} from 'react-testing-library';

describe(
	'ClayToggle',
	() => {
		afterEach(cleanup);
		const mockOnChange = () => {};
		it(
			'should render',
			() => {
				const {asFragment} = render(
					<ClayToggle
						onChange={mockOnChange}
					/>
				);

				expect(asFragment()).toMatchSnapshot();
			}
		);
	}
);
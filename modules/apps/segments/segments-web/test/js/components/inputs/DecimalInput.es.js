import React from 'react';
import DecimalInput from 'components/inputs/DecimalInput.es';
import {cleanup, fireEvent, render} from 'react-testing-library';

const DECIMAL_NUMBER_INPUT_TESTID = 'decimal-number';

describe(
	'DecimalInput',
	() => {
		afterEach(cleanup);

		it(
			'should render type decimal number',
			() => {
				const mockOnChange = jest.fn();

				const defaultNumberValue = '1.23';
				const newNumberValue = '2.34';

				const {asFragment, getByTestId} = render(
					<DecimalInput
						onChange={mockOnChange}
						value={defaultNumberValue}
					/>
				);

				expect(asFragment()).toMatchSnapshot();

				const element = getByTestId(DECIMAL_NUMBER_INPUT_TESTID);

				// Needs to be tested a different way from other inputs since
				// the onChange is called in onBlur rather than on input change.

				expect(element.value).toBe(defaultNumberValue);

				fireEvent.change(
					element,
					{
						target: {value: newNumberValue}
					}
				);

				fireEvent.blur(element);

				expect(element.value).toBe(newNumberValue);
			}
		);
	}
);
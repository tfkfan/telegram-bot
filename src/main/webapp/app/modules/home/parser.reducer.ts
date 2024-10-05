import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { ISearchItem, defaultValue } from 'app/shared/model/search-item.model';

const initialState: EntityState<any> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: null,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/test';

// Actions

export const testCall = createAsyncThunk(
  'parser/test_call',
  async () => {
    return axios.get<String>(`${apiUrl}`);
  },
  { serializeError: serializeAxiosError },
);

// slice

export const ParserSlice = createEntitySlice({
  name: 'parser',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(testCall.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })

      .addMatcher(isPending(testCall), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      });
  },
});

export const { reset } = ParserSlice.actions;

// Reducer
export default ParserSlice.reducer;

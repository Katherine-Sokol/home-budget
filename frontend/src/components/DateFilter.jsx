import React from "react";

const DateFilter = ({
  startDate,
  endDate,
  onChangeStart,
  onChangeEnd,
  onReset,
  onApply,
}) => (
  <div className="row mb-3">
    <div className="col">
      <label>З:</label>
      <input
        type="date"
        value={startDate}
        onChange={(e) => onChangeStart(e.target.value)}
        className="form-control"
      />
    </div>
    <div className="col">
      <label>По:</label>
      <input
        type="date"
        value={endDate}
        onChange={(e) => onChangeEnd(e.target.value)}
        className="form-control"
      />
    </div>
    <div className="col d-flex align-items-end">
      <div className="d-flex w-100 gap-2">
        <button
          onClick={onApply}
          className="btn submit-button w-50"
        >
          Застосувати фільтр
        </button>
        <button
          onClick={onReset}
          className="btn submit-button w-50"
        >
          Скинути
        </button>
      </div>
    </div>
  </div>
);

export default DateFilter;

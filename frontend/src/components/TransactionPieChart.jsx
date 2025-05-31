import React from "react";
import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";

const TransactionPieChart = ({ breakdown, colors, totalAmount }) => (
  <div className="mt-5">
    <h4 className="balance-info p-3 text-center">
      Загальна сума за період: {totalAmount.toFixed(2)} грн
    </h4>
    <div className="transaction-form white d-flex justify-content-center">
      <PieChart
        width={600}
        height={400}
      >
        <Pie
          data={breakdown}
          dataKey="value"
          nameKey="name"
          cx="40%"
          cy="50%"
          outerRadius={100}
          label
        >
          {breakdown.map((_, index) => (
            <Cell
              key={index}
              fill={colors[index]}
            />
          ))}
        </Pie>
        <Tooltip />
        <Legend
          layout="vertical"
          align="right"
          verticalAlign="middle"
        />
      </PieChart>
    </div>
  </div>
);

export default TransactionPieChart;

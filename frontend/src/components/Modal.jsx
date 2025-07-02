import React from "react";
import "./Modal.css";

function Modal({ message, onClose }) {
  return (
    <div className="modal-backdrop">
      <div className="modal-box">
        <p>{message}</p>
        <button onClick={onClose}>OK</button>
      </div>
    </div>
  );
}

export default Modal;

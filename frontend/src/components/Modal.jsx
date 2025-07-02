import React from "react";
import "./Modal.css";

function Modal({ message, onClose }) {
  console.log("Modal component rendered with message:", message);
  return (
    <div className="modal-backdrop">
      <div className="modal-box">
        <p>{message}</p>
        <button
          className="btn submit-button"
          onClick={onClose}
        >
          OK
        </button>
      </div>
    </div>
  );
}

export default Modal;

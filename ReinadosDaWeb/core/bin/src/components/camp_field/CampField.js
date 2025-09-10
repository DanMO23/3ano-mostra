import React from "react";
import { Link } from "react-router-dom";

function CampField({ type, name, id, className, placeholder, value, onChange }) {
  return (
    <input
      required
      type={type}
      name={name}
      id={id}
      className={className}
      placeholder={placeholder}
      value={value}
      onChange={onChange} 
    />
  );
}

export default CampField;

import { useState } from "react";

export const useInput = (initialValue) => {
  const [value, setValue] = useState(initialValue);

  const bind = {
    value,
    onChange: (e) => {
      setValue(e.target.value);
    },
  };

  const reset = () => {
    setValue(initialValue);
  };

  //return 해야 하는 값은 배열 형태의 값
  return [value, bind, reset];
};

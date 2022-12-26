export const nickValidator = (value) => {
  // display name은 숫자와 영문만 가능합니다
  const regexNick = /^[0-9A-Za-z]+$/;
  return regexNick.test(value);
};

export const passwordValidator = (value) => {
  // 비밀번호는 최소 1개의 문자와 1개의 숫자를 포함하여 최소 8자 이상이어야 합니다
  const regexPassword = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
  return regexPassword.test(value);
};

export const emailValidator = (value) => {
  // 이메일 형식이여야 합니다
  const regexEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
  return regexEmail.test(value);
};

import { atom } from "recoil";
// import { recoilPersist } from "recoil-persist";

// const { persistAtom } = recoilPersist();

export const emailState = atom({
  key: "emailState",
  default: null,
});

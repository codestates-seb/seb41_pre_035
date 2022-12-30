const { createProxyMiddleware } = require("http-proxy-middleware");
const ec2Url = "http://ec2-54-180-55-239.ap-northeast-2.compute.amazonaws.com:8080";
const localUrl = "http://localhost:8080";

module.exports = function (app) {
  app.use(
    "/members", //proxy가 필요한 path prameter를 입력합니다.
    createProxyMiddleware({
      target: `${localUrl}`, //타겟이 되는 api url를 입력합니다.
      changeOrigin: true, //대상 서버 구성에 따라 호스트 헤더가 변경되도록 설정하는 부분입니다.
    })
  );
  app.use(
    "/auth/login",
    createProxyMiddleware({
      target: `${localUrl}`,
      changeOrigin: true,
    })
  );
  app.use(
    "/auth/recovery",
    createProxyMiddleware({
      target: `${localUrl}`,
      changeOrigin: true,
    })
  );
  app.use(
    "/auth/logout",
    createProxyMiddleware({
      target: `${localUrl}`,
      changeOrigin: true,
    })
  );
};

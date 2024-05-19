import { Axios } from "@/util/http-common";

const http = Axios();

function listArticle(param, success, fail) {
  http.get(`/board`, { params: param }).then(success).catch(fail);
  console.log("list");
}

function detailArticle(articleno, success, fail) {
  http.get(`/board/${articleno}`).then(success).catch(fail);
  console.log("detail");
}

function registArticle(article, success, fail) {
  // console.log("boardjs article", article);
  http.post(`/board`, JSON.stringify(article)).then(success).catch(fail);
  console.log("regist");
}

function getModifyArticle(articleno, success, fail) {
  // local.get(`/board/modify/${articleno}`).then(success).catch(fail);
  console.log("getmodify");
}

function modifyArticle(article, success, fail) {
  // local.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
  console.log("modify");
}

function deleteArticle(articleno, success, fail) {
  http.delete(`/board/${articleno}`).then(success).catch(fail);
  console.log("delete");
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
};

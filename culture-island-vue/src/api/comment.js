import { Axios } from "@/util/http-common";

const http = Axios();

function listComment(articleno, success, fail) {
  http.get(`/board/comment/${articleno}`).then(success).catch(fail);
  console.log("comment list");
}

function registComment(comment, success, fail) {
  // console.log("boardjs article", article);
  http.post(`/board/comment`, JSON.stringify(comment)).then(success).catch(fail);
  console.log("comment regist");
}

export { listComment, registComment };

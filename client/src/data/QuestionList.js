const QuestionList = {
  data: {
    questionId: 1,
    writer: {
      memberId: 1,
      email: "user01@hello.com",
      name: "user01",
      verificationFlag: false,
      deleteFlag: false,
      lastActivateAt: "2022-12-29T02:54:54.152055",
    },
    title: "title",
    content: "content",
    viewCount: 0,
    voteCount: 0,
    isItWriter: false,
    hasAlreadyVoted: false,
    createdAt: "2022-12-29T02:54:54.152059",
    lastModifiedAt: "2022-12-29T02:54:54.152061",
    tags: [
      {
        tagId: 0,
        name: "java",
      },
      {
        tagId: 1,
        name: "javascript",
      },
      {
        tagId: 2,
        name: "python",
      },
    ],
    answers: [
      {
        answerId: 1,
        questionId: 1,
        memberId: 1,
        content: "답변",
        voteCount: 0,
        isItWriter: true,
        hasAlreadyVoted: false,
        createdAt: "2022-12-29T02:54:54.152086",
        lastModifiedAt: "2022-12-29T02:54:54.152089",
      },
      {
        answerId: 2,
        questionId: 2,
        memberId: 2,
        content: "답변",
        voteCount: 0,
        isItWriter: true,
        hasAlreadyVoted: false,
        createdAt: "2022-12-29T02:54:54.152086",
        lastModifiedAt: "2022-12-29T02:54:54.152089",
      },
    ],
  },
};

export default QuestionList;

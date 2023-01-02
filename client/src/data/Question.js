const Question = {
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
    title: "How to get Telegram audio album artwork using Telethon client API",
    content: `I want to get the URL or bytes of the Telegram audio documents (music) album cover using MTProto API and Telethon Python lib, but I could not find such a thing by checking audio message properties. There was a thumbs property for the message attached media property that was null.

    Note: When I download a media using client.download_media the cover is attached to the file, but I do not want to download it.`,
    viewCount: 0,
    voteCount: 0,
    isItWriter: false,
    hasAlreadyVoted: false,
    createdAt: "2022-12-29T02:54:54.152059",
    lastModifiedAt: "2022-12-29T02:54:54.152061",
    tags: [
      {
        tagId: 0,
        name: "python",
      },
      {
        tagId: 1,
        name: "telethon",
      },
      {
        tagId: 2,
        name: "telegram",
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
    ],
  },
};

export default Question;

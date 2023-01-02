const QuestionList = {
  data: [
    {
      questionId: 1,
      writerId: 1,
      writerName: "user01",
      title: "How to get Telegram audio album artwork using Telethon client API",
      content: `I want to get the URL or bytes of the Telegram audio documents (music) album cover using MTProto API and Telethon Python lib, but I could not find such a thing by checking audio message properties. There was a thumbs property for the message attached media property that was null.

      Note: When I download a media using client.download_media the cover is attached to the file, but I do not want to download it.`,
      voteCount: 0,
      viewCount: 0,
      answerCount: 1,
      createdAt: "2022-12-29T02:54:54.17927",
      lastModifiedAt: "2022-12-29T02:54:54.179272",
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
    },
    {
      questionId: 2,
      writerId: 2,
      writerName: "user02",
      title: "title",
      content: "content",
      voteCount: 0,
      viewCount: 0,
      answerCount: 1,
      createdAt: "2022-12-29T02:54:54.17927",
      lastModifiedAt: "2022-12-29T02:54:54.179272",
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
    },
    {
      questionId: 3,
      writerId: 3,
      writerName: "user03",
      title: "title",
      content: "content",
      voteCount: 0,
      viewCount: 0,
      answerCount: 1,
      createdAt: "2022-12-29T02:54:54.17927",
      lastModifiedAt: "2022-12-29T02:54:54.179272",
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
    },
  ],
  pageInfo: {
    page: 1,
    size: 5,
    totalElements: 10,
    totalPages: 2,
  },
};
export default QuestionList;

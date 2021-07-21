package ru.mousecray.endmagic.client.render.book

import ru.mousecray.endmagic.api.embook.{BookApi, PageContainer}

class BookData {
  var currentPageContainer: PageContainer = BookApi.mainChapter()
  var animationTicks = 0
}

import SwiftUI
import SharedLogic

struct ContentView: View {
    @State private var articles: [Article] = []
    @State private var isLoading = false
    @State private var error: String? = nil
    @State private var selectedSection = "home"
    @State private var selectedArticle: Article? = nil
    @State private var cache: [String: [Article]] = [:]

    private let repository = NewsRepositoryImpl(
        apiService: ApiServiceImpl(),
        articleDao: ArticleDao(
            driverFactory: DatabaseDriverFactory()
        )
    )

    private let sections = [
        "home",
        "technology",
        "world",
        "business",
        "science",
        "arts",
        "politics",
        "sports"
    ]

    var body: some View {
        NavigationView {
            if let article = selectedArticle {
                ArticleDetailView(
                    article: article,
                    onBack: {
                        selectedArticle = nil
                    }
                )
            } else {
                VStack(spacing: 0) {
                    ScrollView(.horizontal, showsIndicators: false) {
                        HStack(spacing: 8) {
                            ForEach(sections, id: \.self) { section in
                                Button {
                                    selectedSection = section
                                    loadNews(section: section)
                                } label: {
                                    Text(section.capitalized)
                                        .font(.subheadline)
                                        .padding(.horizontal, 12)
                                        .padding(.vertical, 6)
                                        .background(
                                            selectedSection == section
                                            ? Color.primary
                                            : Color.clear
                                        )
                                        .foregroundColor(
                                            selectedSection == section
                                            ? Color(.systemBackground)
                                            : Color.primary
                                        )
                                        .cornerRadius(16)
                                        .overlay {
                                            RoundedRectangle(cornerRadius: 16)
                                                .stroke(
                                                    Color.primary,
                                                    lineWidth: 1
                                                )
                                        }
                                }
                            }
                        }
                        .padding(.horizontal)
                        .padding(.vertical, 8)
                    }

                    Divider()

                    if isLoading {
                        Spacer()

                        ProgressView("Loading...")

                        Spacer()
                    } else if let error {
                        Spacer()

                        VStack(spacing: 12) {
                            Text("Error: \(error)")
                                .foregroundColor(.red)
                                .multilineTextAlignment(.center)
                                .padding()

                            Button("Retry") {
                                loadNews(section: selectedSection)
                            }
                        }

                        Spacer()
                    } else if articles.isEmpty {
                        Spacer()

                        Text("No articles available for this section")
                            .foregroundColor(.secondary)

                        Spacer()
                    } else {
                        List(articles, id: \.url) { article in
                            ArticleRowView(article: article)
                                .contentShape(Rectangle())
                                .onTapGesture {
                                    selectedArticle = article
                                }
                        }
                        .listStyle(.plain)
                    }
                }
                .navigationTitle("NYT News")
                .navigationBarTitleDisplayMode(.inline)
            }
        }
        .task {
            loadNews(section: selectedSection)
        }
    }

    private func loadNews(section: String) {
        if let cachedArticles = cache[section] {
            articles = cachedArticles
            return
        }

        isLoading = true
        error = nil

        Task {
            do {
                let result = try await repository.getTopStories(
                    section: section
                )

                await MainActor.run {
                    articles = result
                    cache[section] = result
                    isLoading = false
                }
            } catch {
                await MainActor.run {
                    self.error = error.localizedDescription
                    isLoading = false
                }
            }
        }
    }
}

struct ArticleRowView: View {
    let article: Article

    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            VStack(alignment: .leading, spacing: 4) {
                if !article.kicker.isEmpty {
                    Text(article.kicker.uppercased())
                        .font(.caption)
                        .foregroundColor(.accentColor)
                }

                Text(article.title)
                    .font(.headline)
                    .lineLimit(3)

                Text(article.byline)
                    .font(.caption)
                    .foregroundColor(.secondary)
            }

            Spacer()

            if let thumbnailUrl = article.getThumbnailUrl(),
               let url = URL(string: thumbnailUrl) {
                AsyncImage(url: url) { image in
                    image
                        .resizable()
                        .scaledToFill()
                } placeholder: {
                    Color.gray.opacity(0.2)
                }
                .frame(width: 80, height: 80)
                .clipped()
                .cornerRadius(4)
            }
        }
        .padding(.vertical, 4)
    }
}

struct ArticleDetailView: View {
    let article: Article
    let onBack: () -> Void

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                if let imageUrl = article.getLargeImageUrl(),
                   let url = URL(string: imageUrl) {
                    AsyncImage(url: url) { image in
                        image
                            .resizable()
                            .scaledToFill()
                    } placeholder: {
                        Color.gray.opacity(0.2)
                    }
                    .frame(maxWidth: .infinity)
                    .frame(height: 250)
                    .clipped()
                }

                VStack(alignment: .leading, spacing: 12) {
                    if !article.kicker.isEmpty {
                        Text(article.kicker.uppercased())
                            .font(.caption)
                            .foregroundColor(.accentColor)
                    }

                    Text(article.title)
                        .font(.title2)
                        .fontWeight(.bold)

                    Text(article.byline)
                        .font(.subheadline)
                        .foregroundColor(.secondary)

                    Text(String(article.publishedDate.prefix(10)))
                        .font(.caption)
                        .foregroundColor(.secondary)

                    Divider()

                    Text(article.abstract)
                        .font(.body)

                    if let articleUrl = URL(string: article.url) {
                        Link(
                            "Read Full Article on NYT",
                            destination: articleUrl
                        )
                        .font(.headline)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.accentColor)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                    }
                }
                .padding()
            }
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button(action: onBack) {
                    HStack {
                        Image(systemName: "chevron.left")
                        Text("Back")
                    }
                }
            }
        }
    }
}

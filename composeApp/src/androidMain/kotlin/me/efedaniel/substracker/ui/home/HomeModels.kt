package me.efedaniel.substracker.ui.home

data class HomeUiState(
    val averageMonthlySpendLabel: String,
    val averageMonthlySpendValue: String,
    val deltaPercent: String,
    val insightTitle: String,
    val insightCaption: String,
    val timelineTitle: String,
    val sections: List<TimelineMonthSection>
)

data class TimelineMonthSection(
    val label: String,
    val items: List<TimelineItem>
)

data class TimelineItem(
    val name: String,
    val subtitle: String,
    val amount: String,
    val dueLabel: String
)

enum class HomeTab {
    Timeline,
    Insights,
    Subscriptions,
    Settings
}

internal val sampleHomeState = HomeUiState(
    averageMonthlySpendLabel = "AVERAGE MONTHLY SPEND",
    averageMonthlySpendValue = "$482.50",
    deltaPercent = "+4%",
    insightTitle = "Cash Burn Insight",
    insightCaption = "ACTIVE VS TRIAL PERIODS",
    timelineTitle = "Upcoming Timeline",
    sections = listOf(
        TimelineMonthSection(
            label = "THIS WEEK",
            items = listOf(
                TimelineItem(
                    name = "Spotify Family",
                    subtitle = "Entertainment • Premium Plan",
                    amount = "$16.99",
                    dueLabel = "DUE JUN 14"
                ),
                TimelineItem(
                    name = "Netflix 4K",
                    subtitle = "Entertainment • Shared Account",
                    amount = "$22.99",
                    dueLabel = "DUE JUN 15"
                )
            )
        ),
        TimelineMonthSection(
            label = "NEXT WEEK",
            items = listOf(
                TimelineItem(
                    name = "Adobe Creative Cloud",
                    subtitle = "Work • Annual Prepaid",
                    amount = "$54.99",
                    dueLabel = "DUE JUN 21"
                ),
                TimelineItem(
                    name = "Midjourney Pro",
                    subtitle = "Creative • AI Imaging",
                    amount = "$30.00",
                    dueLabel = "DUE JUN 24"
                )
            )
        )
    )
)

package me.efedaniel.substracker.ui.home

import java.util.UUID

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
    val id: String,
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
                    id = UUID.randomUUID().toString(),
                    name = "Spotify Family",
                    subtitle = "Entertainment • Premium Plan",
                    amount = "$16.99",
                    dueLabel = "DUE JUN 14"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Netflix 4K",
                    subtitle = "Entertainment • Shared Account",
                    amount = "$22.99",
                    dueLabel = "DUE JUN 15"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "iCloud+ 2TB",
                    subtitle = "Storage • Family Sharing",
                    amount = "$9.99",
                    dueLabel = "DUE JUN 16"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "The New York Times",
                    subtitle = "News • Digital + Cooking",
                    amount = "$25.00",
                    dueLabel = "DUE JUN 17"
                )
            )
        ),
        TimelineMonthSection(
            label = "NEXT WEEK",
            items = listOf(
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Adobe Creative Cloud",
                    subtitle = "Work • Annual Prepaid",
                    amount = "$54.99",
                    dueLabel = "DUE JUN 21"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Midjourney Pro",
                    subtitle = "Creative • AI Imaging",
                    amount = "$30.00",
                    dueLabel = "DUE JUN 24"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Figma Professional",
                    subtitle = "Work • Editor Seat",
                    amount = "$15.00",
                    dueLabel = "DUE JUN 25"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "1Password Families",
                    subtitle = "Security • 5 Members",
                    amount = "$4.99",
                    dueLabel = "DUE JUN 26"
                )
            )
        ),
        TimelineMonthSection(
            label = "LATER THIS MONTH",
            items = listOf(
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Notion Plus",
                    subtitle = "Work • Personal Workspace",
                    amount = "$10.00",
                    dueLabel = "DUE JUN 28"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Linear Standard",
                    subtitle = "Work • Single Seat",
                    amount = "$8.00",
                    dueLabel = "DUE JUN 29"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Disney+ Premium",
                    subtitle = "Entertainment • 4K UHD",
                    amount = "$13.99",
                    dueLabel = "DUE JUN 30"
                )
            )
        ),
        TimelineMonthSection(
            label = "NEXT MONTH",
            items = listOf(
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "GitHub Pro",
                    subtitle = "Work • Individual",
                    amount = "$4.00",
                    dueLabel = "DUE JUL 03"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "ChatGPT Plus",
                    subtitle = "AI • Personal",
                    amount = "$20.00",
                    dueLabel = "DUE JUL 05"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Audible Premium",
                    subtitle = "Audio • Monthly Credit",
                    amount = "$14.95",
                    dueLabel = "DUE JUL 08"
                ),
                TimelineItem(
                    id = UUID.randomUUID().toString(),
                    name = "Headspace",
                    subtitle = "Wellness • Annual Billed",
                    amount = "$12.99",
                    dueLabel = "DUE JUL 10"
                )
            )
        )
    )
)
